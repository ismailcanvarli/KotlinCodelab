package com.example.cupcake.test

import androidx.navigation.NavController
import junit.framework.TestCase.assertEquals

//Bunu kotlin file'ı açma amacımız ui ile alakalı test edeceğimiz şeylerde
//belirli assertions(iddiaları) sürekli yapacak olacağız. Bu şeyleri her defasında kopyalamak
// yerine bunu bir sınıfta yazmak daha mantıklı.
fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}