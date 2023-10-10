package com.sample.coroutines

import android.app.ActivityManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.sample.coroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    val TAG = "SydneyTag"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        // Global (live until app is alive)
//        GlobalScope.launch {
//            delay(3000L) // can be called only inside coroutine
//            Log.d(TAG, "coroutines says hello from ${Thread.currentThread().name}")
//            // this will add up the delay of 2 network call
//            val networkCall = doNetworkCall()
//            val networkCall1 = doNetworkCall1()
//            Log.d(TAG, "onCreate: " + networkCall)
//            Log.d(TAG, "onCreate: " + networkCall1)
//        }
//        Log.d(TAG, "main says hello from ${Thread.currentThread().name}")

    // DISPATCHERS
//    - Dispatchers.Main running async code in main thread
//    - Dispatchers.IO networkCalls
//    - Dispatchers.Default long running calculation like (sorting list like 1000)
//    - Dispatchers.Unconfined
//    - newSingleThreadContext("ThreadName")
        //<-- this is combination of network request and ui update (runOnUiThread)
//        GlobalScope.launch(Dispatchers.IO) {
//            val res = doNetworkCall()
//            Log.d(TAG, "onCreate: $res")
//            withContext(Dispatchers.Main) {
//                mBinding.tvText.text = res
//            }
//        }
        // <-- RUN BLOCKING(block the main thread, mostly used in junit testing)
        // run suspend inside main thread
//        runBlocking { // thread.sleep equivalent
//            Log.d(TAG, "onCreate: start block")
//            launch(Dispatchers.IO) {
//                delay(5000L)
//                Log.d(TAG, "onCreate: non block ")
//            }
//            launch(Dispatchers.IO) {
//                delay(5000L)
//                Log.d(TAG, "onCreate: non block 1")
//            }
//            Log.d(TAG, "onCreate: end blocking")
//            delay(3000L)
//        }
        //<--- JOBS, WAITING , CANCELLATION
//        val job = GlobalScope.launch (Dispatchers.Default){
////            repeat(5){
////                Log.d(TAG, "onCreate: job working...")
////
////                delay(1000L)
////            }
//            withTimeout(3000L){
//                for(i in 30 ..40){
//                    if(!isActive){
//                        break
//                    }
//                    Log.d(TAG, "onCreate: time running")
//                    delay(1000L)
//                }
//            }
//        }
//        runBlocking {
//            delay(2000L)
////            job.cancel() // cancel/terminate the job
////            job.join() // wait for the job to be done before executing below code
//            Log.d(TAG, "onCreate: main run")
//        }
        //<--- ASYNC AWAIT
//        GlobalScope.launch(Dispatchers.IO) {
//            val time = measureTimeMillis {
//                val answer1 = async { doNetworkCall() }
//                val answer2 = async { doNetworkCall1() }
//                Log.d(TAG, "onCreate: answer 1 ${answer1.await()}")
//                Log.d(TAG, "onCreate: answer 2 ${answer2.await()}")
//            }
//            Log.d(TAG, "onCreate: req took $time")
//        }
        //<--- LIFE CYCLE AND VIEW MODEL SCOPE
        // (destroy when activity is destroyed)
//        mBinding.button.setOnClickListener{
//            lifecycleScope.launch {
//                while (true){
//                    delay(1000L)
//                    Log.d(TAG, "onCreate: Still running")
//                }
//            }
//            GlobalScope.launch {
//                delay(5000L)
//                Intent(this@MainActivity, SecondActivity::class.java).also{
//                    startActivity(it)
//                    finish()
//                }
//
//            }
//
//        }
        //<--- COROUTINES ON RETROFIT
//        val retrofit = Retrofit.Builder().baseUrl("")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val jsonApi = retrofit.create(Api::class.java)
//        GlobalScope.launch(Dispatchers.IO) {
//            val response = jsonApi.getComments()
//            if(response.isSuccessful){
//                Log.d(TAG, "onCreate: SuccessFul")
//            }
//        }
        //<--- EXCEPTION HANDLING
        val handler = CoroutineExceptionHandler{_, throwable ->
            Log.d(TAG, "onCreate: $throwable")
        }

//        lifecycleScope.launch(handler) {
//             launch {
//                 throw Exception("Error")
//             }
//        }
        // this will run all coroutine and cancel all coroutines below
        // so if you want to still run all even some are failing use supervisorScope
//        CoroutineScope(Dispatchers.Main + handler).launch {
//            supervisorScope {
//                launch {
//                    delay(300L)
//                    throw Exception("coroutine 1 failed!")
//                }
//                launch {
//                    delay(400L)
//                    Log.d(TAG, "onCreate: coroutine 2 finish")
//                }
//            }
//        }
        lifecycleScope.launch {
            val job = launch {
                try {
                    delay(500L)
                }catch (e: Exception){
                    throw e
                }
                Log.d(TAG, "onCreate: coroutine 1 finish")
            }
            delay(300L)
            job.cancel()

        }
    }

    suspend fun doNetworkCall():String{
        delay(3000L)
        return "This is the response"
    }
    suspend fun doNetworkCall1():String{
        delay(3000L)
        return "This is the response 1"
    }
}