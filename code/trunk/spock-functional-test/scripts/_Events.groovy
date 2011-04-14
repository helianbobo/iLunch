def specTestTypeClassName = "grails.plugin.spock.test.GrailsSpecTestType"

def loadedTestTypes = []



softLoadClass = { className ->
	try {
		classLoader.loadClass(className)
	} catch (ClassNotFoundException e) {
		null
	}
}

tryToLoadTestType = { name, typeClassName ->
	if (name in loadedTestTypes) return
	if (!binding.variables.containsKey("functionalTests")) return

	def typeClass = softLoadClass(typeClassName)
	if (typeClass) {
		if (!functionalTests.any { it.class == typeClass }) {
			functionalTests << typeClass.newInstance(name, 'functional')
		}
		loadedTestTypes << name
	}
}


tryToLoadTestTypes = {
	tryToLoadTestType("spock", specTestTypeClassName)
}

eventAllTestsStart = {
    tryToLoadTestTypes()
}