#let has_data_file = "data_file" in sys.inputs
#let data_file = if has_data_file { sys.inputs.data_file } else { "data.csv" }

#set document(
  title: "Statement",
  author: "Some firm over the rainbow",
  keywords: "hello",
  date: auto
)

#set page(
  paper: "us-letter",
  margin: (x: 1.6cm, y: 1.8cm),
  footer: context [
    *Some firm over the rainbow*
    #h(1fr)
    Page
    #counter(page).display(
      "1/1",
      both: true,
    )
  ]
)

= Statement

== Overview

#lorem(50)

#let data = csv(data_file)
#let column_count = data.first().len()

#table(
  fill: (_, y) => if calc.even(y) { rgb("#f3ebff") },
  stroke: none,
  table.header(
    repeat: true,
    ..data.first().map(x => table.cell(fill: rgb("#f3c8ff"), x))
  ),
  columns: (1fr, 1fr, 0.75fr, 0.3fr),
  ..data.slice(1,10).flatten()
)

== Event List

#lorem(20)

#table(
  fill: (_, y) => if calc.even(y) { rgb("#e3ebff") },
  stroke: none,
  table.header(
    repeat: true,
    ..("Row", ..data.first()).map(x => table.cell(fill: rgb("#b3c8ff"), x))
  ),
  columns: (0.2fr, 0.5fr, 2fr, 0.5fr, 0.3fr),
  ..data.slice(1).enumerate().map(((i, row)) => {
    // i + 1 for 1-based indexing
    (table.cell(align: right,text(style: "italic", str(i + 1))), ..row)
  }).flatten()
)

#pagebreak(weak: true)

== Glossary

=== Tomato

The primary option.

=== Soup

Option 2.
