package com.example.yenibihobi.data.order

sealed class OrderStatus(val status: String) {

    object Ordered: OrderStatus("Sipariş Alındı")
    object Canceled: OrderStatus("Sipariş İptal Edildi")
    object Confirmed: OrderStatus("Onaylandı")
    object Shipped: OrderStatus("Gönderildi")
    object Delivered: OrderStatus("Teslim Edildi")
    object Returned: OrderStatus("Geri Gönderildi")
}

fun getOrderStatus(status: String): OrderStatus {
    return when (status) {
        "Ordered" -> {
            OrderStatus.Ordered
        }
        "Canceled" -> {
            OrderStatus.Canceled
        }
        "Confirmed" -> {
            OrderStatus.Confirmed
        }
        "Shipped" -> {
            OrderStatus.Shipped
        }
        "Delivered" -> {
            OrderStatus.Delivered
        }
        else -> OrderStatus.Returned
    }
}