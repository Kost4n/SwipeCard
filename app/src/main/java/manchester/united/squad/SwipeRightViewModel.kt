package manchester.united.squad

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SwipeRightViewModel: ViewModel() {

    private val stream = MutableLiveData<SwipeRightModel>()

    val modelStream: LiveData<SwipeRightModel>
        get() = stream

    private val data = mutableListOf<SwipeRightCardModel>(
        SwipeRightCardModel(R.drawable.acinfeev),
        SwipeRightCardModel(R.drawable.antony), // есть
        SwipeRightCardModel(R.drawable.antonymarsayal),// есть
        SwipeRightCardModel(R.drawable.casemiro),// есть
        SwipeRightCardModel(R.drawable.dzuba),
        SwipeRightCardModel(R.drawable.garrymagyar),// есть
        SwipeRightCardModel(R.drawable.golovin),
        SwipeRightCardModel(R.drawable.holland),
        SwipeRightCardModel(R.drawable.jonyevans),// есть
        SwipeRightCardModel(R.drawable.kobbemainu),// есть
        SwipeRightCardModel(R.drawable.lukshow),// есть
        SwipeRightCardModel(R.drawable.mbappe),
        SwipeRightCardModel(R.drawable.messi),
        SwipeRightCardModel(R.drawable.mesonmayunt),// есть
        SwipeRightCardModel(R.drawable.neimar),
        SwipeRightCardModel(R.drawable.salah),
        SwipeRightCardModel(R.drawable.serhiorehilon),// есть
        SwipeRightCardModel(R.drawable.diigedalot),  //есть
        SwipeRightCardModel(R.drawable.victorvendelef),// есть
        SwipeRightCardModel(R.drawable.zlatan)
    )

    private var currentIndex = 0

    private val topCard
        get() = data[currentIndex % data.size]
    private val bottomCard
        get() = data[(currentIndex + 1) % data.size]

    init {
        shuffle(data)
        updateStream()
    }

    fun swipe() {
        currentIndex += 1
        updateStream()
    }
    private fun updateStream() {
        stream.value = SwipeRightModel(
            top = topCard,
            bottom = bottomCard
        )
    }
    private fun <T> shuffle(list: MutableList<T>) {
        list.shuffle()
    }
}