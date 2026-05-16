; ============================================
; MÓDULO: Validador de Acceso Condicional
; PROBLEMA: Decidir qué mensaje mostrar según comparación
; SOLUCIÓN: cmp + je + dos ramas de ejecución
; ============================================

section .data
    ; === ZONA DE DATOS: Mensajes que se imprimirán ===
    ; "db" = define bytes (cadena de texto)
    ; "10" = salto de línea (LF) para que el cursor baje
    msg_ok      db "Codigo correcto", 10
    len_ok      equ $ - msg_ok        ; Calcula longitud automáticamente
    
    msg_err     db "Codigo incorrecto", 10
    len_err     equ $ - msg_err

section .text
    global _start                     ; Punto de entrada obligatorio

_start:
    ; === FASE 1: CONFIGURACIÓN ===
    ; Cargamos valores en registros para simular entrada de usuario
    mov rax, 1234       ; rax = código maestro (fijo, como en base de datos)
    mov rbx, 1234       ; rbx = código ingresado (cambiar para probar)
    
    ; === FASE 2: COMPARACIÓN ===
    ; cmp NO modifica los registros, solo configura "flags" internos del CPU
    cmp rax, rbx        ; CPU calcula: rax - rbx y guarda el resultado en flags
                        ; Si son iguales → Flag ZF (Zero Flag) = 1
                        ; Si son diferentes → Flag ZF = 0
    
    ; === FASE 3: DECISIÓN (BRANCHING) ===
    ; je = "Jump if Equal": salta a la etiqueta si ZF = 1
    je codigo_correcto  ; Si coinciden, salta a la sección de éxito
    
    ; === RAMA A: CÓDIGO INCORRECTO (se ejecuta si NO saltó) ===
    ; Syscall write: imprimir mensaje de error
    mov rax, 1          ; Número de syscall: 1 = write (escribir)
    mov rdi, 1          ; Primer argumento: 1 = stdout (pantalla)
    mov rsi, msg_err    ; Segundo argumento: dirección del mensaje
    mov rdx, len_err    ; Tercer argumento: cuántos bytes escribir
    syscall             ; ¡Ejecuta la petición al kernel de Linux!
    
    jmp fin_programa    ; Salta al final para NO ejecutar también el "correcto"

codigo_correcto:
    ; === RAMA B: CÓDIGO CORRECTO (se ejecuta si saltó con je) ===
    mov rax, 1          ; syscall: write
    mov rdi, 1          ; stdout
    mov rsi, msg_ok     ; dirección del mensaje de éxito
    mov rdx, len_ok     ; longitud calculada
    syscall             ; Imprime "Codigo correcto"

fin_programa:
    ; === FASE 4: FINALIZACIÓN CONTROLADA ===
    ; SIN ESTO, el programa puede "colgar" o dar error
    mov rax, 60         ; Número de syscall: 60 = exit (terminar)
    mov rdi, 0          ; Argumento: 0 = éxito (código de salida)
    syscall             ; Notifica al SO que terminó y libera recursos
