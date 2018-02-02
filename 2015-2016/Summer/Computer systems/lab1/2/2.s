.global clz
.type clz,@function

.section .text

#algorytm z Hacker's Delight - liczymy liczbe zer wyszukiwaniem binarnym
clz:
	xorl %eax,%eax
	movq $0xFFFFFFFFFFFFFFFF,%r8
	testq %rdi,%r8
	je .L1
	movq $0xFFFFFFFF00000000,%r8
	testq %rdi,%r8
	jne .L2
	addl $32,%eax
	salq $32,%rdi
.L2:
	movq $0xFFFF000000000000,%r8
	testq %rdi,%r8
	jne .L3
	addl $16,%eax
	salq $16,%rdi
.L3:
	movq $0xFF00000000000000,%r8
	testq %rdi,%r8
	jne .L4
	addl $8,%eax
	salq $8,%rdi
.L4:
	movq $0xF000000000000000,%r8
	testq %rdi,%r8
	jne .L5
	addl $4,%eax
	salq $4,%rdi
.L5:
	movq $0xC000000000000000,%r8
	testq %rdi,%r8
	jne .L6
	addl $2,%eax
	salq $2,%rdi
.L6:
	movq $0x8000000000000000,%r8
	testq %rdi,%r8
	jne .L7
	addl $1,%eax
	salq $1,%rdi	
.L7:
	ret
.L1:
	movl $64,%eax
	ret
.size clz,.-clz
