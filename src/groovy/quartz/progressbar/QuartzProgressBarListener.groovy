package quartz.progressbar

import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.listeners.JobListenerSupport


class QuartzProgressBarListener extends JobListenerSupport {
    public static final String NAME = "QuartzProgressBarListener";

    def errorString
	def messageSource

    public String getName() { return NAME; }

    public void jobToBeExecuted(JobExecutionContext ctx) {
        if(!ctx.mergedJobDataMap.get('quartzProgressData')){
            QuartzProgressData quartzProgressData = QuartzProgressDataFactory.getInstance().create()
            ctx.mergedJobDataMap.put('quartzProgressData', quartzProgressData)
            println "Inserted quartzProgressData ${quartzProgressData.id} in quartz job."
        }
    }

    public void jobWasExecuted(JobExecutionContext ctx, JobExecutionException exception) {
		QuartzProgressData quartzProgressData = ctx.mergedJobDataMap.get('quartzProgressData')
		if(quartzProgressData){
	    	if(exception != null) {
	    		def aux = exception
	    		while(aux.getCause()) {
	    			errorString = aux.getCause().getMessage()
	    			aux = aux.getCause()
	    		}
				quartzProgressData.status = 'error'
				quartzProgressData.msg = 'Error: ' + aux.message 
	    	} else {
				quartzProgressData.status = 'done'
	    	}
		}
    }
	
}
