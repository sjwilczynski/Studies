.global adds
.type adds,@function

.section .text

adds:
	movq %rdi,%rax
	movq lmax, %r8
	movq lmin, %r9
	addq %rsi, %rax
	cmovnsq %r9, %r8   #jesli sie nie zapalil znak i byl ov to byl z dolu
	cmovoq %r8,%rax
	retq
	
.size adds, . - adds

.section .rodata
	.align 8
	lmax: .quad 0x7FFFFFFFFFFFFFFF
	.size lmax,8
	lmin: .quad 0x8000000000000000
	.size lmin,8

.type lmax,@object
.type lmin,@object

