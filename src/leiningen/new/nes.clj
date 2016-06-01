(ns leiningen.new.nes
  (:require [leiningen.new.templates :refer :all]
            [leiningen.core.main :as main]))

(def render (renderer "nes"))

(defn nes
  "Forked from leins default template

  Accepts a group id in the project name: `lein new nes-template foo.bar/baz`"
  [name]
  (let [main-ns (multi-segment (sanitize-ns name))
        data {:raw-name name
              :name (project-name name)
              :namespace main-ns
              :nested-dirs (name-to-path main-ns)
              :year (year)
              :date (date)}]
    (main/info "Generating project" name "based on 'nes-template'")
    (main/info "This template is intended for library projects, not applications.")
    (main/info "2 c other templates (app, plugin, etc), try `lein help new`.") ;4 fun
    (->files data
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             [".gitignore" (render "gitignore" data)]
             ["src/{{nested-dirs}}.clj" (render "core.clj" data)]
             ["test/{{nested-dirs}}_test.clj" (render "test.clj" data)]
             ["CHANGELOG.md" (render "CHANGELOG.md" data)]
             "resources")))
