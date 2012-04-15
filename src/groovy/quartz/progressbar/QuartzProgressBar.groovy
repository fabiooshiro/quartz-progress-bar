package quartz.progressbar;

public class QuartzProgressBar {
	
	def quartzScheduler
	
	static String execute(Closure cls){
		QuartzProgressDataFactory quartzProgressDataFactory = QuartzProgressDataFactory.getInstance()
		QuartzProgressData quartzProgressData = quartzProgressDataFactory.create()
		ProgressBarJob.triggerNow(
			'quartzProgressData.id': quartzProgressData.id,
			execute: { ctx ->
				cls(ctx, quartzProgressData)
				quartzProgressData.status = "done"
				//quartzProgressDataFactory.kill(quartzProgressData.id)
			}
		)
		println "Create progress with id = ${quartzProgressData.id}"
		return quartzProgressData.id
	}
	
}
