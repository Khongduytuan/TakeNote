package com.eagletech.takenote.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amazon.device.drm.LicensingService
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.PurchasingService
import com.amazon.device.iap.model.FulfillmentResult
import com.amazon.device.iap.model.ProductDataResponse
import com.amazon.device.iap.model.PurchaseResponse
import com.amazon.device.iap.model.PurchaseUpdatesResponse
import com.amazon.device.iap.model.UserDataResponse
import com.eagletech.takenote.databinding.ActivityPayBinding
import com.eagletech.takenote.sharepref.SharedPreferencesManager

class PayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var currentUserId: String
    private lateinit var currentMarketplace: String

    companion object {
        const val subA = "com.eagletech.takenote.threenotes"
        const val subB = "com.eagletech.takenote.fivenotes"
        const val subC = "com.eagletech.takenote.tennotes"
        const val skuWeek = "com.eagletech.takenote.subpremium"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this)
        setupIAPOnCreate()
        binding.buttonBuy3.setOnClickListener {
            PurchasingService.purchase(subA)
//            sharedPreferencesManager.addLives(2)
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
        }
        binding.buttonBuy5.setOnClickListener {
            PurchasingService.purchase(subB)
            sharedPreferencesManager.addLives(4)
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
        }
        binding.buttonBuy10.setOnClickListener {
            PurchasingService.purchase(subC)
            sharedPreferencesManager.addLives(9)
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
        }
        binding.buttonBuyPremiumWeek.setOnClickListener {
            PurchasingService.purchase(skuWeek)
//            sharedPreferencesManager.addLives(3)
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
        }
        binding.buttonExitApp.setOnClickListener {
//            sharedPreferencesManager.addLives(3)
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }
    }

    private fun setupIAPOnCreate() {
        val purchasingListener: PurchasingListener = object : PurchasingListener {
            override fun onUserDataResponse(response: UserDataResponse) {
//                Log.v("onUserDataResponse response.requestSta tus: ", response.requestStatus.toString())
                when (response.requestStatus!!) {
                    UserDataResponse.RequestStatus.SUCCESSFUL -> {
                        currentUserId = response.userData.userId
                        currentMarketplace = response.userData.marketplace
                        sharedPreferencesManager.currentUserId(currentUserId)
                        Log.v("IAP SDK", "loaded userdataResponse")
                    }

                    UserDataResponse.RequestStatus.FAILED, UserDataResponse.RequestStatus.NOT_SUPPORTED ->                         // Fail gracefully.
                        Log.v("IAP SDK", "loading failed")
                }
            }

            override fun onProductDataResponse(productDataResponse: ProductDataResponse) {
                when (productDataResponse.requestStatus) {
                    ProductDataResponse.RequestStatus.SUCCESSFUL -> {

                        val products = productDataResponse.productData
                        for (key in products.keys) {
                            val product = products[key]
                            //
                            Log.v(
                                "Product:", String.format(
                                    "Product: %s\n Type: %s\n SKU: %s\n Price: %s\n Description: %s\n",
                                    product!!.title,
                                    product.productType,
                                    product.sku,
                                    product.price,
                                    product.description
                                )
                            )

                        }
                        //get all unavailable SKUs
                        for (s in productDataResponse.unavailableSkus) {
                            Log.v("Unavailable SKU:$s", "Unavailable SKU:$s")
                        }
                    }

                    ProductDataResponse.RequestStatus.FAILED -> Log.v("FAILED", "FAILED")
                    else -> {}
                }
            }

            override fun onPurchaseResponse(purchaseResponse: PurchaseResponse) {
                when (purchaseResponse.requestStatus) {
                    PurchaseResponse.RequestStatus.SUCCESSFUL -> {
                        PurchasingService.notifyFulfillment(
                            purchaseResponse.receipt.receiptId,
                            FulfillmentResult.FULFILLED
                        )
                        sharedPreferencesManager.isPremium = !purchaseResponse.receipt.isCanceled
                        Log.v("FAILED", "FAILED")
                    }

                    PurchaseResponse.RequestStatus.FAILED -> {}
                    else -> {}
                }
            }

            override fun onPurchaseUpdatesResponse(response: PurchaseUpdatesResponse) {
                // Process receipts
                when (response.requestStatus) {
                    PurchaseUpdatesResponse.RequestStatus.SUCCESSFUL -> {
                        for (receipt in response.receipts) {
                            sharedPreferencesManager.isPremium = !receipt.isCanceled
                        }
                        if (response.hasMore()) {
                            PurchasingService.getPurchaseUpdates(false)
                        }

//                        val intent = Intent(this@PayActivity, MainActivity::class.java)
//                        startActivity(intent)
//                        finish()
                    }

                    PurchaseUpdatesResponse.RequestStatus.FAILED -> Log.d("FAILED", "FAILED")
                    else -> {}
                }
            }
        }
        PurchasingService.registerListener(this, purchasingListener)
        Log.d(
            "DetailBuyAct",
            "Appstore SDK Mode: " + LicensingService.getAppstoreSDKMode()
        )
    }


    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        PurchasingService.getUserData()
//        binding.btWeek.setOnClickListener { PurchasingService.purchase(skuWeek) }
//        binding.btSubA.setOnClickListener { PurchasingService.purchase(subA) }
//        binding.btSubB.setOnClickListener { PurchasingService.purchase(subB) }
//        binding.btSubC.setOnClickListener { PurchasingService.purchase(subB) }
//        binding.btExit.setOnClickListener { finish() }


        val productSkus: MutableSet<String> = HashSet()
        productSkus.add(skuWeek)
        productSkus.add(subA)
        productSkus.add(subB)
        productSkus.add(subC)
        PurchasingService.getProductData(productSkus)
        PurchasingService.getPurchaseUpdates(false)
    }
}