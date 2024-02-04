package com.example.chatapp.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.constants.CHAT
import com.example.chatapp.constants.USER_NODE
import com.example.chatapp.domain.model.ChatData
import com.example.chatapp.domain.model.ChatUser
import com.example.chatapp.domain.model.UserData
import com.example.chatapp.domain.model.userList
import com.example.chatapp.presentation.navigation.Graph
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class ChatAppViewmodel @Inject constructor(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore
):ViewModel() {

    var inProgress = mutableStateOf(false)
    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn = _isSignedIn.asStateFlow()
    val currentUserData = mutableStateOf<UserData?>(null)
    val chatUser = mutableStateOf<UserData?>(null)
    val chats = mutableStateOf<List<ChatData>>(listOf())
    val allUserData = mutableStateOf<List<ChatData>>(listOf())
    var searchUserResult = mutableStateOf<List<UserData>>(listOf())





    init {
        viewModelScope.launch {
            checkAlreadySignedIn()
            delay(1000)
            _isSignedIn.value = true
        }
    }

    fun checkAlreadySignedIn():String{
        if (auth.currentUser?.email.isNullOrEmpty()){
            return Graph.Authentication.route
        }else{
            getUserdata()
            return Graph.Home.route
        }
    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    private val _persons = MutableStateFlow(userList)

    @OptIn(FlowPreview::class)
    val persons = searchText
        .debounce(1000L)
        .onEach { _isSearching.value = true }
        .combine(_persons) { searchQuery, userDataList ->
            if (searchQuery.isBlank()) {
                // If the search query is blank, return all users
                userDataList
            } else {
                // If there is a search query, filter the users based on the query
                delay(2000L) // You can adjust the delay as needed
                userDataList.filter {
                    it.doesMatchSearchQuery(searchQuery)
                }
            }
        }
        .onEach {
            _isSearching.value = false
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            userList
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }


    //User LogIn
    fun logIn(email: String, password: String,onClickNavigate:()->Unit)
            =viewModelScope.launch{

        inProgress.value = true

        try {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {task->
                    if (task.isSuccessful){
                        getUserdata()
                        onClickNavigate()
                        _isSignedIn.value = true
                        Log.d("TAG","User Successfully Log IN ")
                    }else{
                        inProgress.value = false
                        Log.d("TAG","User Failed Log IN ")
                    }
                }
        }catch (ex:Exception){
            inProgress.value = false
            Log.d("TAG","User Failed Log IN $ex ")
        }

    }


    //Log Out
    fun logOut(){
        inProgress.value = true
        auth.signOut()
        inProgress.value = false
    }


    //For SigningIN
    fun onSignUp(email: String,password: String,name: String?,onCick: () -> Unit){
        inProgress.value = true
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if (task.isSuccessful){
                createNewUser(email,name)
                onCick()
                inProgress.value = false
            }else{
                Log.d("TAG", "Failed $task")
            }
        }
    }


    //Creating New User
    private fun createNewUser(
        email:String? = null,
        name:String? = null,
    )=viewModelScope.launch{
        val uid = auth.uid
        var userData = UserData(
            name = name,
            imageUrl = null,
            bio = "I am Vartalap User",
            email = email,
            uid = uid
        )
        inProgress.value = true
        uid?.let { s ->
            database.collection(USER_NODE).document(s).get().addOnSuccessListener {
                if (it.exists()){
                    //Update user auth
                    inProgress.value = false
                }else{
                    //create new user
                    Log.d("TAG"," Doing $it ")
                    database.collection(USER_NODE).document(uid).set(userData)
                    getUserdata()
                    inProgress.value = false

                }
            }
        }

    }


    //Updating User Data
    fun updateUserData(
        image : String? = null,
        name: String? = null,
        bio:String?= null,
        phone:String? = null
    )=viewModelScope.launch{
        val uid = auth.currentUser?.uid
        val userData = UserData(name,image,bio,phone,uid)

        try {
            uid?.let {uid->
                inProgress.value = true
                database.collection(USER_NODE)
                    .document(uid)
                    .set(userData)
                    .addOnSuccessListener {
                        getUserdata()
                        Log.d("TAG","Data Updated")
                        inProgress.value = false
                    }
            }
        }catch (ex:Exception){
            Log.d("TAG","Failed Update")
            inProgress.value = false
        }

    }


    //gettomg user data
    private fun getUserdata()=
    viewModelScope.launch(){
        val uid = auth.currentUser?.uid
        inProgress.value = true
        if (uid != null) {
            database.collection(USER_NODE).document(uid).get().addOnSuccessListener{ documentSnapshot ->
                if (documentSnapshot.exists()) {
                    currentUserData.value = documentSnapshot.toObject(UserData::class.java)
                    inProgress.value = false
                    populateChats()
                } else {
                    Log.d("TAG","Failed Update")
                    inProgress.value = false
                }
            }
                .addOnFailureListener { e ->
                    Log.d("TAG","Failed Update")
                    inProgress.value = false
                }
            }
    }

     fun getChatUserData(uid:String)=
        viewModelScope.launch(){
            inProgress.value = true
            database.collection(USER_NODE).document(uid).get().addOnSuccessListener{ documentSnapshot ->
                if (documentSnapshot.exists()) {
                    chatUser.value = documentSnapshot.toObject(UserData::class.java)
                    inProgress.value = false
                } else {
                    Log.d("TAG","Failed Update")
                    inProgress.value = false
                }
            }.addOnFailureListener { e ->
                    Log.d("TAG","Failed Update")
                    inProgress.value = false
                }
        }



    fun populateChats(){
        inProgress.value = true
        database.collection(CHAT).where(
            Filter.or(
                Filter.equalTo("user1.uid",currentUserData.value?.uid),
                Filter.equalTo("user2.uid",currentUserData.value?.uid)
            )
        ).get().addOnSuccessListener {
            if (it.isEmpty){
                Log.d("TAG","Failed Update")
            }else{
                chats.value = it.toObjects<ChatData>()
            }
        }
    }


    //get user LIst
    fun getUserList(name: String) {
        if(name.isNotEmpty()){
            database.collection(CHAT).where(Filter.or(
                Filter.and(
                    Filter.equalTo("user1.name",name),
                    Filter.equalTo("user2.name",currentUserData.value?.name)
                ),
                Filter.and(
                    Filter.equalTo("user1.name",currentUserData.value?.name),
                    Filter.equalTo("user2.name",name)
                )
            )).get().addOnSuccessListener { it ->
                if (it.isEmpty){
                    database.collection(USER_NODE).whereEqualTo("name",name).get().addOnSuccessListener { 
                        if (it.isEmpty){
                            Log.d("Tag","No user found")
                        }else{
                            searchUserResult.value = it.toObjects<UserData>()
                            val chatPartner = it.toObjects<UserData>()[0]
                            val id = database.collection(CHAT).document().id
                            val chats = ChatData(
                                chatId = id,
                                ChatUser(
                                    currentUserData.value?.name,
                                    currentUserData.value?.imageUrl,
                                    currentUserData.value?.bio,
                                    currentUserData.value?.email,
                                    currentUserData.value?.uid,
                                ),
                                ChatUser(
                                    chatPartner.name,
                                    chatPartner.imageUrl,
                                    chatPartner.bio,
                                    chatPartner.email,
                                    chatPartner.uid
                                )
                            )
                            database.collection(CHAT).document(id).set(chats)
                            populateChats()
                        }
                    }
                }else{
                    Log.d("Tag","user already exist ")
                }
            }
        }

    }
}