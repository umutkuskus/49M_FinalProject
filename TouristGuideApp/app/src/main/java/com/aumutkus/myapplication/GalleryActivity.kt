package com.aumutkus.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var db : FirebaseFirestore

    var placeNameFromFB : ArrayList<String> = ArrayList()
    var placeInfoFromFB : ArrayList<String> = ArrayList()
    var userImageFromFB : ArrayList<String> = ArrayList()



    var adapter : GalleryRecyclerAdapter? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.custom_options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_place){
            val intent = Intent(applicationContext,UploadPlaceActivity::class.java)
            startActivity(intent)
            finish()
        }else if(item.itemId == R.id.logout){
            auth.signOut()
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        auth = Firebase.auth

        db = FirebaseFirestore.getInstance()

        getDataFromFirestore()


        //recyclerview

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        adapter = GalleryRecyclerAdapter(placeNameFromFB,placeInfoFromFB,userImageFromFB)
        recyclerView.adapter = adapter
    }






    fun getDataFromFirestore() {


        db.collection("Places").orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(applicationContext,exception.localizedMessage.toString(), Toast.LENGTH_LONG).show()
            } else {

                if (snapshot != null) {
                    if (!snapshot.isEmpty) {

                        userImageFromFB.clear()
                        placeInfoFromFB.clear()
                        placeNameFromFB.clear()

                        val documents = snapshot.documents
                        for (document in documents) {
                            val info = document.get("information") as String
                            val hostelName = document.get("placeName") as String
                            val downloadUrl = document.get("downloadUrl") as String
                            val timestamp = document.get("date") as Timestamp
                            val date = timestamp.toDate()

                            placeNameFromFB.add(hostelName)
                            placeInfoFromFB.add(info)
                            userImageFromFB.add(downloadUrl)

                            adapter!!.notifyDataSetChanged()

                        }


                    }
                }

            }
        }

    }
}