interface Config {
    val baseURL: String
}

object DevConfig: Config {
    override val baseURL: String = "https://mocki.io/"
}

object ProdConfig: Config {
    override val baseURL: String = "https://mocki.io/"
}
