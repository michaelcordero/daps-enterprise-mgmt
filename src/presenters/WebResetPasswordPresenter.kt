package presenters

class WebResetPasswordPresenter: AbstractPresenter() {
    companion object Validator {
        val email_pattern: Regex = ("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*+(\\+[_A-Za-z0-9-]+)*@[A-Za-z0-9.-]" +
                "+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})\$").toRegex()
        fun validEmail(email: String): Boolean {
            return email.matches(email_pattern)
        }
    }
}
