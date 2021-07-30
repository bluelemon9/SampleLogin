package com.milkyway.samplelogin

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.milkyway.samplelogin.databinding.ActivityNickNameBinding

class NickNameActivity : AppCompatActivity() {
    // 뷰모델을선언하는방법
    // 우리는ktx를이용하여다음과같이선언
    // -> private val 변수명 : 해당ViewModel by viewModels() (In Activity)
    // -> private val 변수명 : 해당ViewModel by activityViewModels() (In Fragment)
    private val nicknameViewModel : NickNameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 원래코드->setContentView(R.layout.activity_nick_name)
        // 데이터바인딩코드
        // ->Activity이름Binding = DataBindingUitl.setContentView(this, R.layout.레이아웃이름) eg) ActivityNickNameBinding
        // ->Fragment이름Binding = DataBindingUitl.setContentView(this, R.layout.레이아웃이름) eg) FragmentNickNameBinding
        val binding : ActivityNickNameBinding = DataBindingUtil.setContentView(this, R.layout.activity_nick_name)

        //layout에서<data>에선언한viewModel에연결
        binding.viewModel = nicknameViewModel
        binding.lifecycleOwner = this@NickNameActivity

        setToastObserve()
        setNextButtonObserve(binding)
        setTextChangedListener(binding)
    }

    private fun setToastObserve() {
        //뷰모델에있는toast값을관찰하여
        //toast값이true일경우
        //toast를띄워주는코드
        //_toast.value=true참고(35번째줄)
        nicknameViewModel.toast.observe(this, Observer{ toast->
            toast?.let { if(toast) Toast.makeText(this, "중복", Toast.LENGTH_SHORT).show()}
        })

        //이런식으로View는ViewModel을관찰
        //ViewModel에있는여러값들을이용하여뷰를그려주는역할을할뿐
        //값의변경이나어떠한처리와같은모든일들은ViewModel이수행
    }

    private fun setNextButtonObserve(binding : ActivityNickNameBinding) {
        //뷰모델에있는isValid값을관찰하여
        //true일경우와false일경우next버튼색상변경
        nicknameViewModel.isValid.observe(this, Observer{isValid->
            isValid?.let{
                if(isValid) binding.btnNext.setBackgroundColor(Color.GREEN)
                else binding.btnNext.setBackgroundColor(Color.GRAY)
            }
        })
    }
    private fun setTextChangedListener(binding : ActivityNickNameBinding) {
        //데이터바인딩이적용된레이아웃에서 id에접근할경우 binding.etNickname
        //eg)원래접근 et_nickname
        //이코드는텍스트가변화할때boolean값을변경해주기위해서
        //eg)중복확인이완료된이후에변경하고싶은경우reset
        binding.etNickname.textChangedListener {
            if(nicknameViewModel.isValid.value!!){
                Log.d("바인딩1", "${binding.etNickname.text}")
                nicknameViewModel.resetValid()
            }
        }
    }
}