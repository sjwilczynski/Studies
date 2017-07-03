.global fib
.type fib,@function

.section .text

fib:
	pushq %rbp   #trzeba uzyc calle-saved rejestrow bo wywolania rekurencyjne moga przeciez inne nadpisywac
	pushq %rbx
	movl %edi,%ebx
	testl %edi,%edi
	je .L1
	cmpl $1,%edi
	je .L2
	leal -1(%rdi),%edi
	call fib
	movq %rax, %rbp
	leal -2(%rbx),%edi
	call fib
	addq %rbp,%rax
.LK:
	popq %rbx
	popq %rbp	
	ret
.L1:
	movq $0,%rax
	jmp .LK
.L2:
	movq $1,%rax
	jmp .LK
.size fib,.-fib
