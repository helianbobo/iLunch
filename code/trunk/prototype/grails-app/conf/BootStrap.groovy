class BootStrap {

    def fixtureLoader


    def init = { servletContext ->

        environments {
            test {
            }
            development {
                loadData()
            }
        }

    }
    def destroy = {
    }

    void loadData() {
        fixtureLoader.load('initData')
    }
}
