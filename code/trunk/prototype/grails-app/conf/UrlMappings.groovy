class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "/console"(view:"/product/index")
        "500"(view: '/error')
    }
}
