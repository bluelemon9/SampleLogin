package com.milkyway.samplelogin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NickNameViewModel : ViewModel() {

    // MutableLiveData<String>("")초기화
    // TwoWayDataBinding
    // -> viewModel에있는데이터를layout에binding
    // -> layout에서입력되는정보를viewModel에반영
    // -> 그래서Twoway(양방향)
    // -> Binding시킬때 "@={viewModel.nickname}"(양방향)
    val nickname = MutableLiveData<String>("")

    // 닉네임중복확인여부에대한Boolean값
    // View에서관찰할값(Observe)
    // true일경우중복확인버튼이사라지게하는layout코드+next버튼에대한색상변경값
    // false일경우중복확인버튼이보이도록하는layout코드
    private val _isValid = MutableLiveData<Boolean>(false)
    val isValid : LiveData<Boolean>
        get() = _isValid

    // true일경우toast를띄우도록하는값
    private val _toast = MutableLiveData<Boolean>(false)
    val toast : LiveData<Boolean>
        get() = _toast

    fun sameCheck() {
        // 이미저장된값이"min","soo","jin","eun"이라고가정
        // 이미저장된값이랑비교해서
        // _isValid값조정
        if(nickname.value == "min" || nickname.value == "soo" || nickname.value == "jin" || nickname.value == "eun") {
            _isValid.value = false
            _toast.value = true
        } else{
            _isValid.value = true
            Log.d("바인딩", "${nickname.value}")
        }


        //value로접근하는이유는
        //LiveData안에어떠한자료형으로존재하기때문
        //포함관계라고생각하면편할듯?
    }

    fun resetValid() {
        _isValid.value = !_isValid.value!!
    }
}