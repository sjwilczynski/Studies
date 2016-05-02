.global approx_sqrt
.type approx_sqrt,@function

.section .text

approx_sqrt:
	movsd const2, %xmm4 
	movsd const0,%xmm6
	#movsd const1,%xmm7 #zapisujemy jako double 0,2 do dzielenia i porównywania   
	movsd %xmm0, %xmm2 #xmm2 <- x_n	,xmm3 <- x_(n+1)
	movsd const, %xmm3
	addsd %xmm2, %xmm3
	divsd %xmm4, %xmm3 #pierwsza iteracja jakby
	jmp .L1
.L3:
	movsd %xmm3,%xmm8
	vdivsd %xmm3,%xmm0,%xmm3 #xmm3 <- xmm0/xmm3
	addsd %xmm8,%xmm3
	divsd %xmm4,%xmm3
	movsd %xmm8,%xmm2
.L1:
	movsd %xmm3, %xmm5		
	subsd %xmm2, %xmm5
	ucomisd %xmm6, %xmm5 #czy mniejsze od 0
	ja .L2
	movsd %xmm2, %xmm5
	subsd %xmm3, %xmm5  #liczymy tą wartosc bezwzgledna
.L2:
	ucomisd %xmm1,%xmm5  #sprawdzamy warunek pętli while
	jae .L3
	movsd %xmm3, %xmm0
	ret
.size approx_sqrt,.-approx_sqrt

.section .rodata
	const2:	.double 2.0
	#const1: .double -1.0
	const0: .double 0.0
	const: .double 1.0
	.type const2,@object
	.size const2,8
	.type const0,@object
	.size const0,8
	#.type const1,@object
	#.size const1,8
	.type const,@object
	.size const,8
