package org.example.kmpproject.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source               = navigation,
        serializer           = Configuration.serializer(),
        initialConfiguration = Configuration.Home,
        handleBackButton     = true,
        childFactory         = ::createChild
    )

    val currentConfiguration: Configuration
        get() = childStack.value.active.configuration

    fun onTabSelected(config: Configuration) {
        navigation.bringToFront(config)
    }

    private fun createChild(config: Configuration, context: ComponentContext): Child {
        return when (config) {

            is Configuration.Home -> Child.Home(
                HomeComponent(
                    componentContext   = context,
                    onNavigateToProduct = { productId, product, price, image ->
                        navigation.pushNew(Configuration.Product(productId, product, price, image))
                    },
                    onNavigateToCart = { navigation.bringToFront(Configuration.Cart) }
                )
            )

            is Configuration.Cart -> Child.Cart(
                CartComponent(
                    componentContext = context,
                    onGoBack         = { navigation.pop() },
                    onCheckout       = { /* TODO: navigation.pushNew(Configuration.Checkout) */ }
                )
            )

            is Configuration.Fav -> Child.Fav(
                FavComponent(
                    componentContext = context,
                    onGoBack         = { navigation.pop() }
                )
            )

            is Configuration.Profile -> Child.Profile(
                ProfileComponent(
                    componentContext     = context,
                    onGoBack             = { navigation.pop() },
                    onNavigateOrders     = { /* TODO */ },
                    onNavigateCheckout   = { /* TODO */ }
                )
            )

            is Configuration.Product -> Child.Product(
                ProductComponent(
                    productId        = config.productId,
                    product          = config.product,
                    price            = config.price,
                    image            = config.image,
                    onGoBack         = { navigation.pop() },
                    componentContext = context
                )
            )
        }
    }

    // ── Child sealed class ────────────────────────────────────────────────
    sealed class Child {
        data class Home(val component: HomeComponent) : Child() {
            val configuration: Configuration = Configuration.Home
        }
        data class Cart(val component: ICartComponent) : Child() {
            val configuration: Configuration = Configuration.Cart
        }
        data class Fav(val component: IFavComponent) : Child() {
            val configuration: Configuration = Configuration.Fav    // ✅ كان Cart
        }
        data class Profile(val component: IProfileComponent) : Child() {
            val configuration: Configuration = Configuration.Profile // ✅ كان Cart
        }
        data class Product(val component: IProductComponent) : Child() {
            val configuration: Configuration =
                Configuration.Product(component.productId, component.product, component.price, component.image)
        }
    }

    // ── Configuration ─────────────────────────────────────────────────────
    @Serializable
    sealed class Configuration {
        @Serializable data object Home    : Configuration()
        @Serializable data object Cart    : Configuration()
        @Serializable data object Fav     : Configuration()
        @Serializable data object Profile : Configuration()
        @Serializable data class  Product(
            val productId: String,
            val product  : String,
            val price    : String,
            val image    : String
        ) : Configuration()
    }
}