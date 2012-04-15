package quartz.progressbar

public class ProgressBarJob {
	static triggers = {}
	def group = 'DynamicJobs'
	def name = "ProgressBarJob"
	
	def grailsApplication

	def execute(context) {
		Closure cls = context.mergedJobDataMap.get("execute")
		cls(grailsApplication.mainContext)
	}
}
