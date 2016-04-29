
compile:
	./gradlew build
test:
	./gradlew runClient
setup:
	export GRADLE_OPTS=-Xmx2G
	./gradlew setupDecompWorkspace --stacktrace
