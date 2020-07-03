package application

enum class Theme(val css: String) {
    MODERN("/static/css/modern.css"),
    CLASSIC("/static/css/classic.css"),
    DARK("/static/css/dark.css"),
    LIGHT("/static/css/light.css")
}
