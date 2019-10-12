package com.example.wedoit

import com.example.wedoit.repository.entities.ItemEntities
import com.example.wedoit.repository.entities.LocationEntities

interface IGlobalCallbacks {

    interface onCardViewClick{
        fun onStoreCardClick(location : LocationEntities)
    }

    interface OnInquiryClick{
        fun onButtonClick()
    }

}