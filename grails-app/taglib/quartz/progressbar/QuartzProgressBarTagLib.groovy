package quartz.progressbar

class QuartzProgressBarTagLib {

	def quartzProgressBar = { attrs, body ->
		if(attrs.id){
			r.require(module: 'quartzProgressBarJs', disposition: 'head')
			def elId = "QrtzPrgrssBr_${attrs.id}"
			out << """
				<div id="${elId}">
					<div class="msg"></div>
					<div class="progress progress-striped active">
						<div class="bar" style="width: 0%;"></div>
					</div>
				</div>
			""".trim()
			
			out << r.script {
				out << """new quartz.progressbar.ProgressBarView({el: \$('#${elId}'), model: new quartz.progressbar.ProgressData({id: '${attrs.id}'})});"""
			}
		}
	}
}