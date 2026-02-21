package br.com.fabfdev.planner.data.model

data class Profile(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val image: String = "",
) {

    fun isValid(): Boolean =
        name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && image.isNotEmpty()

}