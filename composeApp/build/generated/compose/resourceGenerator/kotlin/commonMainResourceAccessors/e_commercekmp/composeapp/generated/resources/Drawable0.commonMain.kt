@file:OptIn(org.jetbrains.compose.resources.InternalResourceApi::class)

package e_commercekmp.composeapp.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi

private object CommonMainDrawable0 {
  public val compose_multiplatform: DrawableResource by 
      lazy { init_compose_multiplatform() }

  public val pfp: DrawableResource by 
      lazy { init_pfp() }
}

@InternalResourceApi
internal fun _collectCommonMainDrawable0Resources(map: MutableMap<String, DrawableResource>) {
  map.put("compose_multiplatform", CommonMainDrawable0.compose_multiplatform)
  map.put("pfp", CommonMainDrawable0.pfp)
}

internal val Res.drawable.compose_multiplatform: DrawableResource
  get() = CommonMainDrawable0.compose_multiplatform

private fun init_compose_multiplatform(): DrawableResource =
    org.jetbrains.compose.resources.DrawableResource(
  "drawable:compose_multiplatform",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/e_commercekmp.composeapp.generated.resources/drawable/compose-multiplatform.xml", -1, -1),
    )
)

internal val Res.drawable.pfp: DrawableResource
  get() = CommonMainDrawable0.pfp

private fun init_pfp(): DrawableResource = org.jetbrains.compose.resources.DrawableResource(
  "drawable:pfp",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/e_commercekmp.composeapp.generated.resources/drawable/pfp.png", -1, -1),
    )
)
