.global _start
.comm buf,1
.type _start,@function


exit = 60
stdin = 0
read = 0
stdout = 1
write = 1
size = 1

.section .text

_start:

.L1:
	movq $read, %rax #crtl+d to koniec strumienia
	movq $stdin,%rdi
	movq $size, %rdx
	leaq buf,%rsi
	syscall
	testq %rax,%rax  #czy read zwrocil 0
	je .L2 
	cmpq $65,(buf)  #sprawdzamy czy nasz znak jest litera
	jl .L3
	cmpq $122,(buf)
	jg .L3
	cmpq $97,(buf)
	jge .L4
	cmpq $90,(buf)
	jg .L3
	addq $32,(buf)	#czy duza czy mala czy zadna(miedzy 90-97)
.L3:
	movq $write,%rax
	movq $stdout,%rdi
	syscall
	jmp .L1
.L2:
	movq $exit,%rax
	movq $stdout,%rdi
	syscall
.L4:
	subq $32,(buf)
	jmp .L3
.size _start,.-_start
