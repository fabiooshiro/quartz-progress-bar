package quartz.progressbar;

public class ProgressDataController {

	static defaultAction = "show"
	
	def save = {
		render(contentType: "text/json") {
			QuartzProgressDataFactory.getInstance().create(1)
		}	
	}
	
	def show = {
		def id = params.id
		def quartzProgressData = QuartzProgressDataFactory.getInstance().get(id)
		if(!quartzProgressData){
			quartzProgressData = [status: 'killed']
		}
		if(quartzProgressData){
			if(quartzProgressData.step > quartzProgressData.total){
				quartzProgressData.step = quartzProgressData.total
			}
		}
		render(contentType: "text/json") {
			quartzProgressData
		}
	}
    
    def list = {
        def quartzProgressDataList = QuartzProgressDataFactory.getInstance().list()
        render(contentType: "text/json") {
            quartzProgressDataList
        }
    }
    
}
