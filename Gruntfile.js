module.exports = function(grunt){

    // Project configuration
    grunt.initConfig({
        pkg: grunt.file.readJSON("package.json"),

        concat: {
            css: {
                options: {
                    stripBanners: true,
                    banner: '/* <%= pkg.name %> - v<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %> */\n'
                },
                files: {
                    'public/stylesheets/default.css': [
                        'public/stylesheets/src/generics.css',
                        'public/stylesheets/src/default.css'
                    ],
                    'public/stylesheets/modal.css': [
                        'public/stylesheets/src/modal/bet.css',
                        'public/stylesheets/src/modal/refill-payback.css'
                    ],
                    // Files to be refactored
                    'public/stylesheets/refactor.css': [
                        'public/stylesheets/src/inscription.css'
                    ]
                }
            }
        },

        cssmin: {
            target: {
                files:[{
                    expand:true,
                    cwd:'public/stylesheets',
                    src: ['*.css', '!*.min.css'],
                    dest:'public/stylesheets',
                    ext:'.min.css'
                }]
            }
        },

        watch: {
            css: {
                files: [
                    'public/stylesheets/src/*.css',
                    'public/stylesheets/src/modal/*.css'
                ],
                tasks:['concat:css', 'cssmin']
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.registerTask('default', ['concat', 'cssmin']);
    grunt.registerTask('dev', ['watch']);
}