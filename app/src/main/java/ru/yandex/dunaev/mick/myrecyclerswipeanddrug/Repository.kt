package ru.yandex.dunaev.mick.myrecyclerswipeanddrug
import android.content.Context
import kotlinx.serialization.*
import kotlinx.serialization.json.JSON
import org.apache.commons.io.IOUtils
import org.threeten.bp.LocalDateTime
import java.nio.charset.Charset

object Repository{
    lateinit var priceList: MutableList<PricePosition>

    fun init(context: Context) = with(context) {
        resources.openRawResource(R.raw.abb_price).use {
            val st = IOUtils.toString(it, Charset.forName("UTF-8"))
            priceList = JSON.parse(PricePosition.serializer().list, st).toMutableList()
        }
    }
}

@Serializable
data class PricePosition(
    @SerialName("Артикул")
    val vendorCode: String,
    @SerialName("Название товара")
    val productName: String,
    @SerialName("Цена с НДС")
    val priceWithVAT: String,
    @SerialName("Единица")
    val unit: String,
    @SerialName("Актуальность цены")
    val priceRelevance: String
)
