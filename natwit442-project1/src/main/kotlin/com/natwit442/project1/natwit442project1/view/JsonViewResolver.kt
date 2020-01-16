package com.natwit442.project1.natwit442project1.view

import org.springframework.web.servlet.View
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.view.json.MappingJackson2JsonView
import java.util.*


class JsonViewResolver : ViewResolver {

    override fun resolveViewName(viewName: String, locale: Locale): View {

        val view = MappingJackson2JsonView()
        view.setPrettyPrint(true)
        val modelKeys: MutableSet<String> = HashSet()
        modelKeys.add("total_words")
        modelKeys.add("top_ten")
        view.modelKeys = modelKeys
        return view

    }
}