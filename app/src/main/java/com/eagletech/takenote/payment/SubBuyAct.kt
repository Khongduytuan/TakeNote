package com.eagletech.takenote.payment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amazon.device.drm.LicensingService
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.PurchasingService
import com.amazon.device.iap.model.*
import com.eagletech.takenote.databinding.ActivityPurchaseBinding


class SubBuyAct : AppCompatActivity() {
    private lateinit var binding: ActivityPurchaseBinding
    private lateinit var currentUserId: String
    private lateinit var currentMarketplace: String
    private lateinit var quizPref: QuizPref


    companion object {
        const val subA = "com.eagletech.takenote.threenotes"
        const val subB = "com.eagletech.takenote.fivenotes"
        const val subC = "com.eagletech.takenote.tennotes"
        const val skuWeek = "com.eagletech.takenote.subpremium"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        quizPref = QuizPref.instance!!
        setupIAPOnCreate()
    }

    private fun setupIAPOnCreate() {
        val purchasingListener: PurchasingListener = object : PurchasingListener {
            override fun onUserDataResponse(response: UserDataResponse) {
                when (response.requestStatus!!) {
                    UserDataResponse.RequestStatus.SUCCESSFUL -> {
                        currentUserId = response.userData.userId
                        currentMarketplace = response.userData.marketplace
                        quizPref.currentUserId(currentUserId)
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
                        quizPref.isPremium = !purchaseResponse.receipt.isCanceled
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
                            quizPref.isPremium = !receipt.isCanceled
                        }
                        if (response.hasMore()) {
                            PurchasingService.getPurchaseUpdates(false)
                        }
                    }

                    PurchaseUpdatesResponse.RequestStatus.FAILED -> Log.d("FAILED", "FAILED")
                    else -> {}
                }
            }
        }
        PurchasingService.registerListener(this.applicationContext, purchasingListener)
        Log.d(
            "DetailBuyAct",
            "Appstore SDK Mode: " + LicensingService.getAppstoreSDKMode()
        )
    }

    override fun onStart() {
        super.onStart()
        val productSkus: MutableSet<String> = HashSet()
        productSkus.add(skuWeek)
        productSkus.add(subA)
        productSkus.add(subB)
        productSkus.add(subC)
        PurchasingService.getProductData(productSkus)
    }

    override fun onResume() {
        super.onResume()
        PurchasingService.getUserData()
        binding.btWeek.setOnClickListener { PurchasingService.purchase(skuWeek) }
        binding.btSubA.setOnClickListener { PurchasingService.purchase(subA) }
        binding.btSubB.setOnClickListener { PurchasingService.purchase(subB) }
        binding.btSubC.setOnClickListener { PurchasingService.purchase(subB) }
        binding.btExit.setOnClickListener { finish() }
        PurchasingService.getPurchaseUpdates(false)
    }
}