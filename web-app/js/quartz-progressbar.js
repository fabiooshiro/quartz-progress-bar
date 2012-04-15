namespace("quartz.progressbar", function(model, view){
	
	model("ProgressData", {
		msg: "",
		step: 0,
		total: 100,
		status: "waiting", // done, killed, error
		percent: function(){
			return this.get('step') / this.get('total') * 100.0;
		}
	})
	
	view("ProgressBarView", {
		initialize: function(){
			var self = this;
			_.bindAll(self, 'render');
			this.model.bind('change', this.render);
			this.intervalId = setInterval(function() {
				self.model.fetch();
			}, 1000);
		},
		render: function(){
			var percent = this.model.get('percent').apply(this.model);
			if(this.model.get('status') == 'killed'){
				$(this.el).find(".progress").hide(500);
				clearInterval(this.intervalId);
			}else if(this.model.get('status') == 'done'){
				$(this.el).find(".progress").removeClass("active");	
			}else if(this.model.get('status') == 'error'){
				$(this.el).find(".progress").removeClass("active");	
			}
			$(this.el).find(".msg").text(this.model.get('msg'));
			$(this.el).find(".bar").css('width', percent + '%');
		}
	}); 
	
});