(eval-when (:compile-toplevel :load-toplevel :execute) (require :log4cl))

(defvar BASE-DIR "~/Downloads/")
(defvar STORAGE-SIZE 30016144) ;TODO: change from kb to MB or GB
(defvar INSTALL-DIR "~/dev/BackupBot/")
(defvar NO-MEMORY nil)

(defstruct pmeasure desirable undesirable one-chunk-consumed)
(defvar *PMEASURE* (make-pmeasure :desirable 100000 :undesirable -49999 :one-chunk-consumed -1))

(defstruct file path size extension status features)
(defstruct feature type name desirable total)
(defvar *all-files* '())

(defun run-bash-program (name &key (input nil) (path nil path-supplied-p))
  (with-input-from-string (str (concatenate 'string name " " input))
    (let ((output
            (with-output-to-string (out)
              (uiop:run-program "/bin/bash" :input str :output out :error-output out))))
      (log:info "Ran ~A: ~A" name output)
      (return-from run-bash-program output))))

(defun run-tool (tool-name &optional (input nil))
  "Runs TOOL-NAME which lives in INSTALL-DIR. Assumes it is a bash program."
  (run-bash-program (concatenate 'string INSTALL-DIR tool-name) input))

#+dont(defun get-files ()
  (run-tool "constructFind.sh")
  (with-input-from-string (str (run-bash-program (concatenate 'string INSTALL-DIR "findCom.sh")))
    (let ((find-output 
            (with-output-to-string (out-stream )
              (uiop:run-program "/bin/bash" :input str :output out-stream :error-output out-stream))))
      (with-input-from-string (res output)
        (do ((line (read-line res nil nil) (read-line res nil nil)))
            ((null line)
             line)
          (log:info "~A" line)
          (setf *all-files* (push (make-file :path line) *all-files*)))))))
  
