
compile:
	./gradlew build --offline
test:
	./gradlew runClient --offline
setup:
	export GRADLE_OPTS=-Xmx2G
	./gradlew setupDecompWorkspace --stacktrace
