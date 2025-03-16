import net.merchantpug.krendershowcase.gradle.Properties
import net.merchantpug.krendershowcase.gradle.Versions

plugins {
	id("fabric-loom") version("1.10-SNAPSHOT")
}

version = Versions.MOD
group = Properties.GROUP

base {
	archivesName = Properties.MOD_ID
}

repositories {
	maven("https://maven.parchmentmc.org") {
		name = "ParchmentMC"
	}
	maven("https://maven.kneelawk.com/releases") {
		name = "Kneelawk's Maven"
	}
	maven("https://maven.terraformersmc.com/") {
		name = "TerraformersMC"
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${Versions.MINECRAFT}")
	mappings(loom.layered() {
		officialMojangMappings()
		parchment("org.parchmentmc.data:parchment-${Versions.MINECRAFT}:${Versions.PARCHMENT}@zip")
	})
	modImplementation("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")
	modImplementation("net.fabricmc.fabric-api:fabric-api:${Versions.FABRIC_API}")
	modLocalRuntime("com.terraformersmc:modmenu:${Versions.MOD_MENU}")

	modImplementation("com.kneelawk.krender:krender-engine-api-fabric:${Versions.KRENDER}")
	include("com.kneelawk.krender:krender-engine-api-fabric:${Versions.KRENDER}")
	modImplementation("com.kneelawk.krender:krender-engine-backend-frapi:${Versions.KRENDER}")
	include("com.kneelawk.krender:krender-engine-backend-frapi:${Versions.KRENDER}")
	modImplementation("com.kneelawk.krender:krender-model-loading-fabric:${Versions.KRENDER}")
	include("com.kneelawk.krender:krender-model-loading-fabric:${Versions.KRENDER}")
	modImplementation("com.kneelawk.krender:krender-model-guard-fabric:${Versions.KRENDER}")
	include("com.kneelawk.krender:krender-model-guard-fabric:${Versions.KRENDER}")
	modImplementation("com.kneelawk.krender:krender-model-gltf-fabric:${Versions.KRENDER}")
	include("com.kneelawk.krender:krender-model-gltf-fabric:${Versions.KRENDER}")
	modImplementation("com.kneelawk.krender:krender-model-obj-fabric:${Versions.KRENDER}")
	include("com.kneelawk.krender:krender-model-obj-fabric:${Versions.KRENDER}")
}

tasks {
	named<Jar>("jar").configure {
		from(rootProject.file("LICENSE")) {
			rename { "${it}_${Properties.MOD_NAME}" }
		}
	}

	val expandProps = mapOf(
		"mod_version" to Versions.MOD,
		"group" to project.group, //Else we target the task's group.
		"minecraft_version" to Versions.MINECRAFT,
		"fabric_api_version" to Versions.FABRIC_API,
		"fabric_loader_version" to Versions.FABRIC_LOADER,
		"fabric_minecraft_version_range" to Versions.FABRIC_MINECRAFT_RANGE,
		"fabric_loader_range" to Versions.FABRIC_LOADER_RANGE,
		"mod_name" to Properties.MOD_NAME,
		"mod_author" to Properties.MOD_AUTHOR,
		"mod_contributors" to Properties.MOD_CONTRIBUTORS.joinToString(separator = "\",\n\t\t\""),
		"mod_id" to Properties.MOD_ID,
		"mod_license" to Properties.LICENSE,
		"mod_description" to Properties.DESCRIPTION,
		"java_version" to Versions.JAVA,
		"sources" to Properties.GITHUB_REPO
	)

	withType<ProcessResources>().configureEach {
		inputs.properties(expandProps)
		filesMatching(setOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "*.mixins.json")) {
			expand(expandProps)
		}
		exclude("\\.cache")
	}
}