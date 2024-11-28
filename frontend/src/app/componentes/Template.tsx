import React from "react";

interface TemplateProps {
  children: React.ReactNode
}

export const Template: React.FC<TemplateProps> = (temp: TemplateProps) => {
  return (
    <>
      <Header/>
      {temp.children}
      <Footer/>
    </>
  );
}

const Header: React.FC = () => {
  return (
    <header>
      <h4>Tcia</h4>
    </header>
  )
}

const Footer: React.FC = () => {
  return (
    <footer>
      <h1>Tcia</h1>
    </footer>
  )
}
