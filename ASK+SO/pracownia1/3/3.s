.global lcm_gcd
.type lcm_gcd,@function

.section .text

lcm_gcd:
	movq %rsi, %r9
	movq %rdi, %r8
	jmp .L1
.L2:
	movq $0,%rdx     #tutaj standarodwy algorytm euklidesa
	movq %r8,%rax
	divq %r9		 #instrukcja div S: rdx <- rdx:rax mod S
	movq %r9,%r8						#rax <- rdx:rax / S
	movq %rdx, %r9
.L1:
	testq %r9,%r9
	jne .L2	 		#sprawdzanie warunku z petli while
	movq %rdi,%rax
	movq $0, %rdx   #najpierw dzielimy potem mnozymy zeby uwazac na overflow
	divq %r8
	mulq %rsi # w rax lcm    mulq S : %rdx:%rax <- S*%rax
	movq %r8,%rdx
	retq
.size lcm_gcd,.-lcm_gcd
