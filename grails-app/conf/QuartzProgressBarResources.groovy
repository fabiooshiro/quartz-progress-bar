modules = {
	underscore{
		dependsOn 'jquery'
		resource url: 'js/underscore-1.3.1.js'
	}
	backbone{
		dependsOn 'jquery, underscore'
		resource url: 'js/backbone.js'
	}
	backboneGrails{
		dependsOn 'jquery, backbone'
		resource url: 'js/backbone-grails.js'
	}
	namespacejs{
		dependsOn 'backboneGrails'
		resource url: 'js/namespace.js'
	}
	bootstrap{
		resource url: 'css/bootstrap/bootstrap.css'
		resource url: 'css/bootstrap/bootstrap-responsive.css'
		resource url: 'js/bootstrap.js'
	}
    quartzProgressBarJs {
		dependsOn 'bootstrap, backboneGrails, namespacejs'
        resource url:'js/quartz-progressbar.js'
    }
}