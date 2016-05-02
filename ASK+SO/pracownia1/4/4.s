.global insert_sort
.type insert_sort,@function

.section .text

insert_sort:
	movq $1,%rcx
	addq $8,%rsi
	subq %rdi,%rsi
	sarq $3,%rsi  #licze rozmiar tablicy
	cmpq $1,%rsi  #jak mala to od razu posortwana
	jle .L4
.L3:
	movq %rcx,%rdx
	movq %rdx,%r9
	decq %r9
.L1:
	movq (%rdi,%r9,8), %r11
	cmpq %r11,(%rdi,%rdx,8)  #sprawdzam czy zrobic swapa
	jge .L2					 
	movq (%rdi,%rdx,8),%r8
	movq (%rdi,%r9,8),%r11
	movq %r11,(%rdi,%rdx,8)
	movq %r8, (%rdi,%r9,8)   #swap
	decq %rdx
	decq %r9
	cmpq $1,%rdx             #czy przesuwac dalej w tablicy
	jge .L1
.L2:
	incq %rcx				#nastepny krok zewnetrznej petli
	cmpq %rsi,%rcx			#dopoki indeksem nie wychodzimy poza tablice
	jl .L3
.L4:
	ret	
.size insert_sort,.-insert_sort
