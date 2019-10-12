package com.example.wedoit.repository

import com.example.wedoit.repository.entities.HeaderEntities
import com.example.wedoit.repository.entities.HistoryEntities
import com.example.wedoit.repository.entities.ItemEntities
import com.example.wedoit.repository.entities.StoreEntities

object FakeData {

    val mockListOfStores : ArrayList<StoreEntities>
        get() {
            val arrayList = ArrayList<StoreEntities>()

            arrayList.add(
                StoreEntities(
                    "Ultra-Power Laundry",
                    "56 Malasimbo St. Brgy Masambong Quezon City",
                    -0.0050,
                    0.0007,
                    "https://www.lamudi.com.ph/static/media/bm9uZS9ub25l/2x2x5x880x396/9e162355d21b7d.jpg",
                    60.00
                )
            )

            arrayList.add(
                StoreEntities(
                    "Genius Jester Laundry",
                    "56 Malasimbo St. Brgy Masambong Quezon City",
                    0.0050,
                    0.0008,
                    "https://www.lamudi.com.ph/static/media/bm9uZS9ub25l/2x2x5x880x396/9e162355d21b7d.jpg",
                    65.00
                )
            )

            arrayList.add(
                StoreEntities(
                    "Laundry Mo To",
                    "56 Malasimbo St. Brgy Masambong Quezon City",
                    -0.0020,
                    0.0025,
                    "https://upload.wikimedia.org/wikipedia/commons/7/7e/Araneta_Center_%28Cubao%2C_Quezon_City%29%282017-08-13%29.jpg",
                    55.00
                )
            )

            arrayList.add(
                StoreEntities(
                    "Akin na Labahan ko",
                    "56 Malasimbo St. Brgy Masambong Quezon City",
                    0.0020,
                    -0.0015,
                    "https://upload.wikimedia.org/wikipedia/commons/7/7e/Araneta_Center_%28Cubao%2C_Quezon_City%29%282017-08-13%29.jpg",
                    60.00
                )
            )

            arrayList.add(
                StoreEntities(
                    "Labahan ko damit mo",
                    "56 Malasimbo St. Brgy Masambong Quezon City",
                    0.0,
                    0.0005,
                    "https://upload.wikimedia.org/wikipedia/commons/7/7e/Araneta_Center_%28Cubao%2C_Quezon_City%29%282017-08-13%29.jpg",
                    65.00
                )
            )

            return arrayList
        }

    fun mockStoreEntities(storeName : String) : StoreEntities?{
        for (store in mockListOfStores){
            if (store.storeName == storeName){
                return store
            }
        }
        return null
    }

    val mockListOfAds : ArrayList<String>
        get() {
            val arrayList = ArrayList<String>()

            arrayList.add("https://pbs.twimg.com/media/CBgI5_mU8AALI91.png")
            arrayList.add("https://adobomagazine.com/wp-content/uploads/2018/02/mcdonalds_best-tasting_chicken_mcdo_and_world_famous_fries_half.jpg")
            arrayList.add("https://payload.cargocollective.com/1/8/266867/3928567/T2-10059-1-SonOfBaconator-PauseAd-768x432_o.jpg")
            arrayList.add("https://pbs.twimg.com/media/DtbQlQxUUAAD6NE.jpg")

            return arrayList
        }

    val mockListOfHistory : ArrayList<HistoryEntities>
        get() {
            val arrayList = ArrayList<HistoryEntities>()
            arrayList.add(HistoryEntities("Parking Services","Reserve parking for the tomorrow's party near albama hotel"))
            arrayList.add(HistoryEntities("Parking Services","Reserve parking for the tomorrow's party near albama hotel"))
            arrayList.add(HistoryEntities("Parking Services","Reserve parking for the tomorrow's party near albama hotel"))
            arrayList.add(HistoryEntities("Parking Services","Reserve parking for the tomorrow's party near albama hotel"))
            arrayList.add(HistoryEntities("Parking Services","Reserve parking for the tomorrow's party near albama hotel"))
            arrayList.add(HistoryEntities("Parking Services","Reserve parking for the tomorrow's party near albama hotel"))
            arrayList.add(HistoryEntities("Parking Services","Reserve parking for the tomorrow's party near albama hotel"))
            arrayList.add(HistoryEntities("Parking Services","Reserve parking for the tomorrow's party near albama hotel"))
            arrayList.add(HistoryEntities("Parking Services","Reserve parking for the tomorrow's party near albama hotel"))

            return arrayList
        }


    val mockListOfItems : ArrayList<ItemEntities>
        get() {
            val arrayList = ArrayList<ItemEntities>()

            arrayList.add(ItemEntities(1,"Wash Service",1,60.00,1))
            arrayList.add(ItemEntities(2,"Dry Service",1,60.00,1))
            arrayList.add(ItemEntities(3,"Fold Service",1,20.00,1))
            arrayList.add(ItemEntities(4,"Full Service",1,135.00,1))

            return arrayList
        }

    val mockListOfServiceItems : ArrayList<ItemEntities>
        get() {
            val arrayList = ArrayList<ItemEntities>()

            arrayList.add(ItemEntities(1,"Wash Service",1,60.00,1))
            arrayList.add(ItemEntities(2,"Dry Service",1,60.00,1))
            arrayList.add(ItemEntities(3,"Fold Service",1,20.00,1))
            arrayList.add(ItemEntities(4,"Full Service",1,135.00,1))


            return arrayList
        }
    val mockListOfDetergentItems : ArrayList<ItemEntities>
        get() {
            val arrayList = ArrayList<ItemEntities>()


            arrayList.add(ItemEntities(5,"Tide Liquid Gel",2,10.00,1))
            arrayList.add(ItemEntities(6,"Ariel Liquid Gel",2,12.00,1))
            arrayList.add(ItemEntities(7,"Breeze Liquid Gel",2,15.00,1))

            return arrayList
        }
    val mockListOfFabricConditionerItems : ArrayList<ItemEntities>
        get() {
            val arrayList = ArrayList<ItemEntities>()

            arrayList.add(ItemEntities(8,"Downy Fabcon",3,20.00,1))

            return arrayList
        }

    val mockListOfHeaderServices : ArrayList<HeaderEntities>
        get() {
            val arrayList = ArrayList<HeaderEntities>()

            arrayList.add(HeaderEntities(1,"Services",1,1))
            arrayList.add(HeaderEntities(2,"Detergents",1,2))
            arrayList.add(HeaderEntities(3,"Fabric Conditioner",1,3))
            arrayList.add(HeaderEntities(4,"Others",1,3))

            return arrayList
        }
}