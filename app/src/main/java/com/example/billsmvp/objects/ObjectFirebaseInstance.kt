package com.example.billsmvp.objects

import com.example.billsmvp.models.Despesa
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object ObjectFirebaseInstance {
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    lateinit var querySnapshot : QuerySnapshot


    fun getUId () : String? {
        return auth.currentUser?.uid
    }

    fun create(collection: String,doc : String,value : Any,completion: (success: Boolean) -> Unit){
        db.collection(collection).document(doc).set(value).addOnSuccessListener {
            completion(true)
        }.addOnFailureListener {
            completion(false)
        }
    }

    fun<T> getCurrentMonthOrded(collection: String,classType : Class<T>,completion: (success: Boolean, list : List<T>) -> Unit) {
        db.collection(collection).whereEqualTo("userId", getUId()).addSnapshotListener { querySnapshot, erro ->

            querySnapshot?.let {
             it.query.orderBy("data", Query.Direction.DESCENDING).addSnapshotListener{ querySnapshot, erro ->

                 querySnapshot?.let {
                     it.toObjects(classType)
                     val list = it.map {
                         it.toObject(classType)
                     }
                     completion(true, list)
                 }
                 erro.let {
                     completion(false, arrayListOf())
                 }
             }
            }




        }

    }

     fun<T> getAll(collection: String,classType : Class<T>,completion: (success: Boolean, list : List<T>) -> Unit) {
        db.collection(collection).whereEqualTo("userId", getUId()).addSnapshotListener { querySnapshot, erro ->
            querySnapshot?.let {
                it.toObjects(classType)
                val list = it.map {
                    it.toObject(classType)
                }
                completion(true, list)
            }
            erro.let {
                completion(false, arrayListOf())
            }
        }

    }

    suspend fun<T> getAllAsync(collection: String,classType : Class<T>,completion: (success: Boolean, list : List<T>) -> Unit) {
        querySnapshot = db.collection(collection).whereEqualTo("userId", getUId()).get().await()

        querySnapshot.let {
            completion(true, it.let {
                it.map {
                    it.toObject(classType)
                }
            })
        }
    }


    fun update(collection: String, value: Any,id: String,
                  completion: (sucess: Boolean) -> Unit) {
        db.collection(collection).document(id).set(value).addOnSuccessListener {
            completion(true)
        }
            .addOnFailureListener {
                completion( false)
            }
    }

    fun delete(collection: String,id: String){
        db.collection(collection).document(id).delete()
    }

}