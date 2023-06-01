package com.example.yenibihobi.data

sealed class Category(val category: String){
    object Sayi : Category("Sayi")
    object Taki : Category("Taki")
    object Maket : Category("Maket")
    object Trid : Category("Trid")
    object Sarf : Category("Sarf")

}
