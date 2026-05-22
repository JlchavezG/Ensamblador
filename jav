section .data
  msg_ok  db "Codigo correcto", 10   ; Mensaje éxito
  len_ok  equ $ - msg_ok             ; Longitud automática
  msg_err db "Codigo incorrecto", 10 ; Mensaje error
  len_err equ $ - msg_err            ; $ = posición actual

section .text
  global _start                      ; Punto de entrada

_start:
  ; === FASE 1: CONFIGURACIÓN ===
  mov rax, 1234   ; rax = código maestro (fijo)
  mov rbx, 1234   ; rbx = código ingresado

  ; === FASE 2: COMPARACIÓN ===
  cmp rax, rbx    ; Compara, actualiza ZF

  ; === FASE 3: DECISIÓN ===
  je codigo_correcto  ; Si ZF=1, salta

  ; === RAMA A: INCORRECTO ===
  mov rax, 1      ; syscall: write
  mov rdi, 1      ; stdout
  mov rsi, msg_err
  mov rdx, len_err
  syscall
  jmp fin_programa    ; Salta al final

codigo_correcto:
  ; === RAMA B: CORRECTO ===
  mov rax, 1
  mov rdi, 1
  mov rsi, msg_ok
  mov rdx, len_ok
  syscall

fin_programa:
  ; === FASE 4: FINALIZACIÓN ===
  mov rax, 60     ; syscall: exit
  mov rdi, 0      ; código de salida: 0
  syscall
