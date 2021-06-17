package com.nac.sampleapp.view.api.model

import com.google.gson.annotations.SerializedName
import lombok.Getter
import lombok.Setter
import lombok.ToString
import java.io.Serializable

@Getter
@Setter
@ToString
class AccountEntity(@field:SerializedName("phone") private val mPhone: String, @field:SerializedName("password") private val mPass: String) : Serializable