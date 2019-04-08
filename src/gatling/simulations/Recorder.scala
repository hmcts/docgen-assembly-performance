import io.gatling.recorder.GatlingRecorder
import io.gatling.recorder.config.RecorderPropertiesBuilder

object Recorder extends App {

	val props = new RecorderPropertiesBuilder()
		.simulationsFolder(IDEPathHelper.recorderOutputDirectory.toString)
		.simulationPackage("uk.gov.hmcts.reform.docgen.scenarios")
		.resourcesFolder(IDEPathHelper.bodiesDirectory.toString)

	GatlingRecorder.fromMap(props.build, Some(IDEPathHelper.recorderConfigFile))
}
