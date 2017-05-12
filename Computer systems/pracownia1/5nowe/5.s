.global mulf
.type mulf,@function

bias = 127
.section .text

mulf:
	movl %edi, %r8d
	movl %esi, %r9d
	shll $9, %r9d
	shrl $9, %r9d
	shll $9, %r8d
	shrl $9, %r8d    #mantysy
	movl %edi, %r10d
	movl %esi, %r11d
	andl $0x7FFFFFFF, %r10d  
	andl $0x7FFFFFFF, %r11d  
	shrl $23, %r10d
	shrl $23, %r11d #wykładniki
	movl %edi, %eax
	andl $0x80000000, %eax
	andl $0x80000000, %esi
	xorl %esi, %eax
	andl $0x80000000, %eax #znak juz w eaxie
	addl %r11d, %r10d
	subl $bias, %r10d
	orl  $0x800000, %r8d
	orl  $0x800000, %r9d   #dodanie jedynek z przodu do mnozenia mantys
	imulq %r9,%r8
	shrq $23,%r8
	testl $0x1000000, %r8d #jesli za duza mantysa ponaprawiaj
	je .L1
	shrl $1,%r8d
	addl $1,%r10d
.L1:
	andl $0x7FFFFF, %r8d
	shll $23, %r10d
	orl %r10d, %eax
	orl %r8d, %eax
	ret 	
.size mulf,.-mulf
