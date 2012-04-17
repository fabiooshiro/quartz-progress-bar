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
	
	var ProgressDataList = Backbone.Collection.extend({
	    model: quartz.progressbar.ProgressData
	});
	
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
			return this;
		}
	});
	
	var MProgressBarView = Backbone.Marionette.ItemView.extend({
        template: "#quartz_progress_bar_item",
        tagName: 'tr',
        className: 'QuartzProgBar',
        initialEvents: function(){
            _.bindAll(this, 'render');
            if (this.collection){
                this.bindTo(this.collection, "reset", this.render, this);
            }
            if(this.model){
                var self = this;
                self.modelRaw = progressBarViewCached[this.model.id];
                this.modelRaw.bind('change', this.render);
            }
        },
        htmlDone: false,
        render: function(){
            var that = this;
            this.beforeRender && this.beforeRender();
            this.trigger("item:before:render", that);
            if(!this.htmlDone){
                $(this.el).html(
                    '<td>                                                    ' +
                    '    <div>                                               ' +
                    '        <div class="msg"></div>                         ' +
                    '        <div class="progress progress-striped active">  ' +
                    '            <div class="bar" style="width: 0%;"></div>  ' +
                    '        </div>                                          ' +
                    '    </div>                                              ' +
                    '</td>                                                   '
                );
                this.htmlDone = true;
            }
            var model = this.modelRaw;
            var percent = this.model.get('percent').apply(this.model);
            if(model.get('status') == 'killed'){
                $(this.el).find(".progress").hide(500);
            }else if(model.get('status') == 'done'){
                $(this.el).find(".progress").removeClass("active"); 
            }else if(model.get('status') == 'error'){
                $(this.el).find(".progress").removeClass("active"); 
            }
            $(this.el).find(".msg").text(model.get('msg'));
            $(this.el).find(".bar").css('width', percent + '%'); 
            console.log(model.id + " percent = " + percent);
            that.onRender && that.onRender();
            that.trigger("item:rendered", that);
            that.trigger("render", that);
            return this;
        }
    });
	
	MProgressBarListView = Backbone.Marionette.CompositeView.extend({
	    tagName: "table",
	    id: "progressbarList",
	    className: "table-striped table-bordered",
	    template: ".quartz_progress_bar",
	    itemView: MProgressBarView,
	    appendHtml: function(collectionView, itemView){
	        collectionView.$("tbody").append(itemView.el);
	    }
	});
	
	var progressBarViewCached = {};
	ProgressBarListView = function(options){
	    if(!options.progressbars){
	        
	        var progressDataList = new ProgressDataList();
	        progressDataList.url = config.contextPath + "/progressData/list";
	        progressDataList.fetch({
	            success: function(collection, response){
	                collection.forEach(function(item){
	                    progressBarViewCached[item.id] = item;
	                });
	                new MProgressBarListView({el: options.el, collection: collection}).render();
	            }
	        });
	        
	        var progressDataList2 = new ProgressDataList();
	        progressDataList2.url = config.contextPath + "/progressData/list";
	        setInterval(function() {
	            progressDataList2.fetch({
	                success: function(collection, response){
	                    var removeList = [];
	                    var addList = [];
	                    var newProgressBarViewCached = {};
	                    for(var i = 0; i < collection.models.length; i++){
	                        var item = collection.models[i]; 
	                        var localItem = progressBarViewCached[item.id];
	                        if(localItem && localItem.get('version') != item.get('version')){
	                            var attrsUpd = {};
	                            for(attrName in localItem.attributes){
	                                if(localItem.get(attrName) != item.get(attrName)){
	                                    attrsUpd[attrName] = item.get(attrName);
	                                }
	                            }
	                            localItem.set(attrsUpd);
	                            newProgressBarViewCached[item.id] = localItem;
	                        }else{
	                            addList.push(item);
	                            newProgressBarViewCached[item.id] = item;
	                        }
	                    }
	                    progressDataList.forEach(function(item){
	                       if(!newProgressBarViewCached[item.id]){
	                           removeList.push(item);
	                       }
	                    });
	                    progressBarViewCached = newProgressBarViewCached;
	                    progressDataList.remove(removeList);
	                    progressDataList.add(addList);
	                    
	                }
	            });
	        }, 1000);
	    }else{
	        new MProgressBarListView({el: options.el, collection: options.progressbars}).render();    
	    }
	};
	
	return {
	    ProgressBarListView: ProgressBarListView
	};
});



