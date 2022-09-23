//package utilities
//
//import com.icerockdev.service.email.MailerService
//import com.icerockdev.service.email.SMTPConfig
//import com.icerockdev.service.email.SMTPSecure
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import model.User
//
//object EmailService {
//    val mailer: MailerService = MailerService(CoroutineScope(Dispatchers.IO),
//        SMTPConfig(host = "smtp.gmail.com",
//            port = 587,
//            smtpSecure = SMTPSecure.TLS,
//            smtpAuth = true,
//            username = "corderosoft@gmail.com",
//            password = "zslgufvfsnvskfij")
//    )
//    fun send_email(user: User,password: String): Job {
//       return mailer.compose().apply {
//            fromEmail = "corderosoft@gmail.com"
//            fromName = "Dental Auxiliary Placement Services"
//            subject = "Password Reset"
//            to = mutableMapOf(user.email to user.first_name+" "+user.last_name)
//            html = "<p>This is a password reset notification for ${user.first_name+" "+user.last_name}<p>\n" +
//                    "<p>Your new password has been reset to: <b>$password</b></p>" +
//                    "<p>You may change it in the DAPS application at anytime, under the settings page.</p>"
//        }.sendAsync()
//    }
//}
