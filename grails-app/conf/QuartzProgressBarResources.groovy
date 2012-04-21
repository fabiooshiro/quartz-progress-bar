modules = {
	underscore{
		dependsOn 'jquery'
		resource url: 'js/underscore-1.3.1.js', disposition: 'head'
	}
	backbone{
		dependsOn 'jquery, underscore'
		resource url: 'js/backbone.js', disposition: 'head'
	}
    marionette{
        dependsOn 'backbone'
        resource url: 'js/backbone.marionette.js', disposition: 'head'
    }
	backboneGrails{
		dependsOn 'jquery, backbone'
		resource url: 'js/backbone-grails.js', disposition: 'head'
	}
	namespacejs{
		dependsOn 'backboneGrails'
		resource url: 'js/namespace.js', disposition: 'head'
	}
	bootstrap{
		resource url: 'css/bootstrap/bootstrap.css', disposition: 'head'
		resource url: 'css/bootstrap/bootstrap-responsive.css', disposition: 'head'
		resource url: 'js/bootstrap.js', disposition: 'head'
	}
    trafficCop{
        dependsOn 'jquery'
        resource url: 'js/TrafficCop.min.js', disposition: 'head'
    }
    quartzProgressBarJs {
		dependsOn 'bootstrap, backboneGrails, namespacejs, marionette, trafficCop'
        resource url: 'js/quartz-progressbar.js', disposition: 'head'
    }
}